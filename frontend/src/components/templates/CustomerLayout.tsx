import type { ReactNode } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useCartStore } from '../../stores/useCartStore';
import { useAuthStore } from '../../stores/useAuthStore';

export default function CustomerLayout({ children }: { children: ReactNode }) {
  const nav = useNavigate();
  const loc = useLocation();
  const totalQty = useCartStore((s) => s.totalQuantity());
  const logout = useAuthStore((s) => s.logout);

  return (
    <div>
      <div className="nav flex-between">
        <span className="font-bold text-lg" style={{ cursor: 'pointer' }} onClick={() => nav('/customer/menu')}>☕ 테이블오더</span>
        <div className="flex gap-8">
          <button className={`nav-tab ${loc.pathname === '/customer/menu' ? 'active' : ''}`} onClick={() => nav('/customer/menu')} data-testid="nav-menu">메뉴</button>
          <button className={`nav-tab ${loc.pathname === '/customer/cart' ? 'active' : ''}`} onClick={() => nav('/customer/cart')} data-testid="nav-cart">장바구니{totalQty > 0 && ` (${totalQty})`}</button>
          <button className={`nav-tab ${loc.pathname === '/customer/orders' ? 'active' : ''}`} onClick={() => nav('/customer/orders')} data-testid="nav-orders">주문내역</button>
          <button className="nav-tab" onClick={() => { logout(); nav('/'); }} data-testid="nav-settings">⚙️ 설정</button>
        </div>
      </div>
      <div className="container">{children}</div>
    </div>
  );
}
