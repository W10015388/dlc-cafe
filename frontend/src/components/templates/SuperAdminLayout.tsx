import type { ReactNode } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

export default function SuperAdminLayout({ children }: { children: ReactNode }) {
  const nav = useNavigate();
  const loc = useLocation();

  return (
    <div>
      <div className="nav flex-between">
        <span className="font-bold text-lg">⚙️ 슈퍼 관리자</span>
        <div className="flex gap-8">
          <button className={`nav-tab ${loc.pathname.startsWith('/super/stores') ? 'active' : ''}`} onClick={() => nav('/super/stores')} data-testid="nav-stores">매장</button>
          <button className={`nav-tab ${loc.pathname === '/super/admins' ? 'active' : ''}`} onClick={() => nav('/super/admins')} data-testid="nav-admins">관리자</button>
          <button className={`nav-tab ${loc.pathname === '/super/categories' ? 'active' : ''}`} onClick={() => nav('/super/categories')} data-testid="nav-categories">카테고리</button>
          <button className={`nav-tab ${loc.pathname.startsWith('/super/menus') ? 'active' : ''}`} onClick={() => nav('/super/menus')} data-testid="nav-menus">메뉴</button>
          <button className="nav-tab" onClick={() => nav('/')} data-testid="nav-home">홈</button>
        </div>
      </div>
      <div className="container">{children}</div>
    </div>
  );
}
