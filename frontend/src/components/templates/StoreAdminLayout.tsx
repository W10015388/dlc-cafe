import type { ReactNode } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

export default function StoreAdminLayout({ children }: { children: ReactNode }) {
  const nav = useNavigate();
  const loc = useLocation();

  return (
    <div>
      <div className="nav flex-between">
        <span className="font-bold text-lg">🏪 매장 관리</span>
        <div className="flex gap-8">
          <button className={`nav-tab ${loc.pathname === '/admin/dashboard' ? 'active' : ''}`} onClick={() => nav('/admin/dashboard')} data-testid="nav-dashboard">대시보드</button>
          <button className="nav-tab" onClick={() => nav('/')} data-testid="nav-home">홈</button>
        </div>
      </div>
      <div className="container">{children}</div>
    </div>
  );
}
