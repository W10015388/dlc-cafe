import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCartStore } from '../../stores/useCartStore';
import { useAuthStore } from '../../stores/useAuthStore';
import { api } from '../../services/api';
import type { Order } from '../../types';
import CustomerLayout from '../../components/templates/CustomerLayout';
import Button from '../../components/atoms/Button';

export default function OrderConfirmPage() {
  const { items, totalAmount, clear } = useCartStore();
  const { tableId, setSessionToken } = useAuthStore();
  const [orderNumber, setOrderNumber] = useState<string | null>(null);
  const [error, setError] = useState('');
  const nav = useNavigate();

  const handleOrder = async () => {
    try {
      const res = await api.post<Order>('/orders', {
        tableId,
        items: items.map((i) => ({
          menuId: i.menuId,
          quantity: i.quantity,
          selectedOptions: i.selectedOptions.map((o) => ({ optionId: o.optionId })),
        })),
      });
      setOrderNumber(res.orderNumber);
      if (res.sessionId) setSessionToken(String(res.sessionId));
      clear();
      setTimeout(() => nav('/customer/menu'), 5000);
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : '주문 실패');
    }
  };

  if (orderNumber) {
    return (
      <CustomerLayout>
        <div className="card text-center" style={{ padding: 40 }}>
          <div className="text-xl font-bold mb-16">✅ 주문 완료!</div>
          <div className="text-lg mb-8">주문번호: {orderNumber}</div>
          <div className="text-gray">5초 후 메뉴 화면으로 이동합니다</div>
        </div>
      </CustomerLayout>
    );
  }

  return (
    <CustomerLayout>
      <h2 className="text-xl font-bold mb-16">주문 확인</h2>
      {error && <p style={{ color: 'red', marginBottom: 8 }}>{error}</p>}
      {items.map((item) => (
        <div key={item.cartItemId} className="card mb-8">
          <div className="flex-between">
            <span className="font-bold">{item.menuName} × {item.quantity}</span>
            <span>{(item.unitPrice * item.quantity).toLocaleString()}원</span>
          </div>
          {item.selectedOptions.length > 0 && (
            <div className="text-sm text-gray">{item.selectedOptions.map((o) => `${o.optionName}`).join(', ')}</div>
          )}
        </div>
      ))}
      <div className="card mt-16">
        <div className="flex-between mb-16">
          <span className="text-lg font-bold">총 금액</span>
          <span className="text-lg font-bold">{totalAmount().toLocaleString()}원</span>
        </div>
        <div className="flex gap-8">
          <Button variant="secondary" onClick={() => nav('/customer/cart')} style={{ flex: 1 }} data-testid="order-back">뒤로</Button>
          <Button onClick={handleOrder} style={{ flex: 2 }} data-testid="order-confirm">주문 확정</Button>
        </div>
      </div>
    </CustomerLayout>
  );
}
