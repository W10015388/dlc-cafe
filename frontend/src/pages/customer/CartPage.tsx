import { useNavigate } from 'react-router-dom';
import { useCartStore } from '../../stores/useCartStore';
import CustomerLayout from '../../components/templates/CustomerLayout';
import Button from '../../components/atoms/Button';

export default function CartPage() {
  const { items, removeItem, updateQuantity, clear, totalAmount } = useCartStore();
  const nav = useNavigate();

  return (
    <CustomerLayout>
      <h2 className="text-xl font-bold mb-16">장바구니</h2>
      {items.length === 0 ? (
        <div className="card text-center text-gray" style={{ padding: 40 }}>장바구니가 비어있습니다</div>
      ) : (
        <>
          {items.map((item) => (
            <div key={item.cartItemId} className="card mb-8" data-testid={`cart-item-${item.cartItemId}`}>
              <div className="flex-between mb-8">
                <span className="font-bold">{item.menuName}</span>
                <Button variant="danger" onClick={() => removeItem(item.cartItemId)} data-testid={`remove-${item.cartItemId}`} style={{ minWidth: 32, minHeight: 32, padding: '4px 8px' }}>✕</Button>
              </div>
              {item.selectedOptions.length > 0 && (
                <div className="text-sm text-gray mb-8">
                  {item.selectedOptions.map((o) => `${o.groupName}: ${o.optionName}`).join(', ')}
                </div>
              )}
              <div className="flex-between">
                <div className="flex gap-8" style={{ alignItems: 'center' }}>
                  <Button variant="secondary" onClick={() => updateQuantity(item.cartItemId, item.quantity - 1)} data-testid={`qty-minus-${item.cartItemId}`} style={{ minWidth: 32, padding: '4px 8px' }}>-</Button>
                  <span>{item.quantity}</span>
                  <Button variant="secondary" onClick={() => updateQuantity(item.cartItemId, item.quantity + 1)} data-testid={`qty-plus-${item.cartItemId}`} style={{ minWidth: 32, padding: '4px 8px' }}>+</Button>
                </div>
                <span className="font-bold">{(item.unitPrice * item.quantity).toLocaleString()}원</span>
              </div>
            </div>
          ))}
          <div className="card mt-16">
            <div className="flex-between mb-16">
              <span className="text-lg font-bold">총 금액</span>
              <span className="text-lg font-bold">{totalAmount().toLocaleString()}원</span>
            </div>
            <div className="flex gap-8">
              <Button variant="secondary" onClick={clear} data-testid="cart-clear" style={{ flex: 1 }}>비우기</Button>
              <Button onClick={() => nav('/customer/order-confirm')} data-testid="cart-order" style={{ flex: 2 }}>주문하기</Button>
            </div>
          </div>
        </>
      )}
    </CustomerLayout>
  );
}
