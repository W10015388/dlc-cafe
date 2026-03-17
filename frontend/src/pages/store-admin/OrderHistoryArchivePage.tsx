import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { api } from '../../services/api';
import type { OrderHistory } from '../../types';
import StoreAdminLayout from '../../components/templates/StoreAdminLayout';

export default function OrderHistoryArchivePage() {
  const { tableId } = useParams();
  const [histories, setHistories] = useState<OrderHistory[]>([]);

  useEffect(() => {
    if (tableId) api.get<OrderHistory[]>(`/order-histories?tableId=${tableId}`).then(setHistories);
  }, [tableId]);

  return (
    <StoreAdminLayout>
      <h2 className="text-xl font-bold mb-16">테이블 {tableId} 과거 주문 이력</h2>
      {histories.length === 0 ? (
        <div className="card text-center text-gray" style={{ padding: 40 }}>이력이 없습니다</div>
      ) : (
        histories.map((h) => (
          <div key={h.id} className="card mb-8" data-testid={`history-${h.id}`}>
            <div className="flex-between mb-8">
              <span className="font-bold">{h.orderNumber}</span>
              <span className="text-sm text-gray">{new Date(h.completedAt).toLocaleString('ko-KR')}</span>
            </div>
            <div className="text-right font-bold">{h.totalAmount.toLocaleString()}원</div>
          </div>
        ))
      )}
    </StoreAdminLayout>
  );
}
