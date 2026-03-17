import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import RoleSelectPage from './pages/common/RoleSelectPage';
import TableSetupPage from './pages/customer/TableSetupPage';
import MenuPage from './pages/customer/MenuPage';
import CartPage from './pages/customer/CartPage';
import OrderConfirmPage from './pages/customer/OrderConfirmPage';
import OrderHistoryPage from './pages/customer/OrderHistoryPage';
import DashboardPage from './pages/store-admin/DashboardPage';
import OrderHistoryArchivePage from './pages/store-admin/OrderHistoryArchivePage';
import StoreListPage from './pages/super-admin/StoreListPage';
import StoreFormPage from './pages/super-admin/StoreFormPage';
import AdminAccountPage from './pages/super-admin/AdminAccountPage';
import CategoryPage from './pages/super-admin/CategoryPage';
import MenuListPage from './pages/super-admin/MenuListPage';
import MenuFormPage from './pages/super-admin/MenuFormPage';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<RoleSelectPage />} />
        <Route path="/customer/setup" element={<TableSetupPage />} />
        <Route path="/customer/menu" element={<MenuPage />} />
        <Route path="/customer/cart" element={<CartPage />} />
        <Route path="/customer/order-confirm" element={<OrderConfirmPage />} />
        <Route path="/customer/orders" element={<OrderHistoryPage />} />
        <Route path="/admin/dashboard" element={<DashboardPage />} />
        <Route path="/admin/history/:tableId" element={<OrderHistoryArchivePage />} />
        <Route path="/super/stores" element={<StoreListPage />} />
        <Route path="/super/stores/new" element={<StoreFormPage />} />
        <Route path="/super/stores/:id/edit" element={<StoreFormPage />} />
        <Route path="/super/admins" element={<AdminAccountPage />} />
        <Route path="/super/categories" element={<CategoryPage />} />
        <Route path="/super/menus" element={<MenuListPage />} />
        <Route path="/super/menus/new" element={<MenuFormPage />} />
        <Route path="/super/menus/:id/edit" element={<MenuFormPage />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
