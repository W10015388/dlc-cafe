import { useEffect } from 'react';
import { api } from '../../services/api';
import { subscribeSSE } from '../../services/sse';
import { useAuthStore } from '../../stores/useAuthStore';
import { useOrderStore } from '../../stores/useOrderStore';
import type { Order } from '../../types';
import CustomerLayout from '../../components/templates/CustomerLayout';
import Badge from '../../components/atoms/Badge';

export default function OrderHistoryPage() {
  const { tableId, sessionToken } = useAuthStore();
  const { orders, setOrders, updateOrderStatus, removeOrder } = useOrderStore();

  useEffect(() => {
    if (!sessionToken) return;
    api.get<Order[]>(`/orders?sessionId=${sessionToken}`).then(setOrders);
  }, [sessionToken, setOrders]);

  useEffect(() => {
    if (!tableId) return;
    const es = subscribeSSE(`/table/${tableId}`, (type, data: unknown) => {
      const d = data as Record<string, unknown>;
      if (type === 'STATUS_CHANGED') updateOrderStatus(d.orderId as number, d.status as Order['status']);
      if (type === 'ORDER_DELETED') removeOrder(d.orderId as number);
    });
    return () => es.close();
  }, [tableId, updateOrderStatus, removeOrder]);

  return (
    <CustomerLayout>
      <h2 className="text-xl font-bold mb-16">주문 내역</h2>
      {orders.length === 0 ? (
        <div className="card text-center text-gray" style={{ padding: 40 }}>주문 내역이 없습니다</div>
      ) : (
        orders.map((o) => (
          <div key={o.id} className="card mb-8" data-testid={`order-${o.id}`}>
            <div className="flex-between mb-8">
              <span className="font-bold">{o.orderNumber}</span>
              <Badge status={o.status} />
            </div>
            {o.items.map((item) => (
              <div key={item.id} className="mb-8">
                <div className="flex-between">
                  <span>{item.menuName} × {item.quantity}</span>
                  <span>{(item.unitPrice * item.quantity).toLocaleString()}원</span>
                </div>
                {item.options.length > 0 && (
                  <div className="text-sm text-gray">{item.options.map((op) => `${op.optionGroupName}: ${op.optionName}`).join(', ')}</div>
                )}
              </div>
            ))}
            <div className="text-right font-bold">{o.totalAmount.toLocaleString()}원</div>
          </div>
        ))
      )}
    </CustomerLayout>
  );
}
