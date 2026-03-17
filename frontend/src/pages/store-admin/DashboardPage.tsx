import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../../services/api';
import { subscribeSSE } from '../../services/sse';
import { useAuthStore } from '../../stores/useAuthStore';
import type { TableInfo, Order } from '../../types';
import StoreAdminLayout from '../../components/templates/StoreAdminLayout';
import Modal from '../../components/atoms/Modal';
import Button from '../../components/atoms/Button';
import Badge from '../../components/atoms/Badge';

export default function DashboardPage() {
  const { selectedStoreId } = useAuthStore();
  const [tables, setTables] = useState<TableInfo[]>([]);
  const [orders, setOrders] = useState<Record<number, Order[]>>({});
  const [selectedTable, setSelectedTable] = useState<TableInfo | null>(null);
  const [showSetup, setShowSetup] = useState(false);
  const [newTableNum, setNewTableNum] = useState('');
  const [newTablePw, setNewTablePw] = useState('');
  const nav = useNavigate();

  const loadData = async () => {
    if (!selectedStoreId) return;
    const tbls = await api.get<TableInfo[]>(`/tables?storeId=${selectedStoreId}`);
    setTables(tbls);
    // Load orders for each table's active session (simplified: load all)
    const orderMap: Record<number, Order[]> = {};
    for (const t of tbls) {
      try {
        // We need session-based query; for MVP, we'll use a simplified approach
        const tableOrders = await api.get<Order[]>(`/orders?sessionId=${t.id}`).catch(() => []);
        orderMap[t.id] = tableOrders;
      } catch { orderMap[t.id] = []; }
    }
    setOrders(orderMap);
  };

  useEffect(() => { loadData(); }, [selectedStoreId]);

  useEffect(() => {
    if (!selectedStoreId) return;
    const es = subscribeSSE(`/store/${selectedStoreId}`, () => loadData());
    return () => es.close();
  }, [selectedStoreId]);

  const handleStatusChange = async (orderId: number) => {
    await api.patch(`/orders/${orderId}/status`);
    loadData();
  };

  const handleDeleteOrder = async (orderId: number) => {
    if (!confirm('주문을 삭제하시겠습니까?')) return;
    await api.delete(`/orders/${orderId}`);
    loadData();
  };

  const handleComplete = async (tableId: number) => {
    if (!confirm('이용 완료 처리하시겠습니까?')) return;
    await api.post(`/order-histories/archive/${tableId}`);
    loadData();
    setSelectedTable(null);
  };

  const handleCreateTable = async () => {
    await api.post('/tables', { storeId: selectedStoreId, tableNumber: Number(newTableNum), password: newTablePw });
    setShowSetup(false);
    setNewTableNum('');
    setNewTablePw('');
    loadData();
  };

  const tableOrders = selectedTable ? (orders[selectedTable.id] || []) : [];

  return (
    <StoreAdminLayout>
      <div className="flex-between mb-16">
        <h2 className="text-xl font-bold">주문 대시보드</h2>
        <Button onClick={() => setShowSetup(true)} data-testid="add-table">+ 테이블 추가</Button>
      </div>
      <div className="grid grid-3">
        {tables.map((t) => {
          const tOrders = orders[t.id] || [];
          const total = tOrders.reduce((s, o) => s + o.totalAmount, 0);
          const hasNew = tOrders.some((o) => o.status === 'PENDING');
          return (
            <div key={t.id} className={`card table-card ${tOrders.length > 0 ? 'has-orders' : ''}`}
              style={{ cursor: 'pointer', animation: hasNew ? 'pulse 1s infinite' : undefined }}
              onClick={() => setSelectedTable(t)} data-testid={`table-${t.tableNumber}`}>
              <div className="flex-between mb-8">
                <span className="font-bold text-lg">테이블 {t.tableNumber}</span>
                {tOrders.length > 0 && <span className="badge badge-pending">{tOrders.length}건</span>}
              </div>
              <div className="text-gray text-sm">{total > 0 ? `${total.toLocaleString()}원` : '주문 없음'}</div>
              <div className="mt-16 flex gap-8">
                <Button variant="secondary" onClick={(e) => { e.stopPropagation(); nav(`/admin/history/${t.id}`); }} data-testid={`history-${t.id}`} style={{ fontSize: 12 }}>이력</Button>
                <Button variant="success" onClick={(e) => { e.stopPropagation(); handleComplete(t.id); }} data-testid={`complete-${t.id}`} style={{ fontSize: 12 }}>이용완료</Button>
              </div>
            </div>
          );
        })}
      </div>

      <Modal isOpen={!!selectedTable} onClose={() => setSelectedTable(null)}>
        {selectedTable && (
          <>
            <h3 className="text-lg font-bold mb-16">테이블 {selectedTable.tableNumber} 주문 상세</h3>
            {tableOrders.length === 0 ? <p className="text-gray">주문이 없습니다</p> : (
              tableOrders.map((o) => (
                <div key={o.id} className="card mb-8">
                  <div className="flex-between mb-8">
                    <span className="font-bold">{o.orderNumber}</span>
                    <Badge status={o.status} />
                  </div>
                  {o.items.map((item) => (
                    <div key={item.id} className="mb-8">
                      <div>{item.menuName} × {item.quantity} — {(item.unitPrice * item.quantity).toLocaleString()}원</div>
                      {item.options.length > 0 && <div className="text-sm text-gray">{item.options.map((op) => `${op.optionGroupName}: ${op.optionName}`).join(', ')}</div>}
                    </div>
                  ))}
                  <div className="flex gap-8 mt-16">
                    {o.status !== 'COMPLETED' && <Button onClick={() => handleStatusChange(o.id)} data-testid={`advance-${o.id}`}>다음 상태</Button>}
                    <Button variant="danger" onClick={() => handleDeleteOrder(o.id)} data-testid={`delete-order-${o.id}`}>삭제</Button>
                  </div>
                </div>
              ))
            )}
          </>
        )}
      </Modal>

      <Modal isOpen={showSetup} onClose={() => setShowSetup(false)}>
        <h3 className="text-lg font-bold mb-16">테이블 추가</h3>
        <div className="form-group">
          <label>테이블 번호</label>
          <input type="number" value={newTableNum} onChange={(e) => setNewTableNum(e.target.value)} data-testid="new-table-number" />
        </div>
        <div className="form-group">
          <label>비밀번호</label>
          <input value={newTablePw} onChange={(e) => setNewTablePw(e.target.value)} data-testid="new-table-password" />
        </div>
        <Button onClick={handleCreateTable} style={{ width: '100%' }} data-testid="create-table-submit">추가</Button>
      </Modal>
    </StoreAdminLayout>
  );
}
