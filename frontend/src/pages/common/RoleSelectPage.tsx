import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../../services/api';
import { useAuthStore } from '../../stores/useAuthStore';
import type { Store } from '../../types';
import Button from '../../components/atoms/Button';

export default function RoleSelectPage() {
  const [stores, setStores] = useState<Store[]>([]);
  const [showStoreSelect, setShowStoreSelect] = useState(false);
  const [error, setError] = useState('');
  const { setRole, setSelectedStore, setTableAuth, tableId } = useAuthStore();
  const nav = useNavigate();

  useEffect(() => { api.get<Store[]>('/stores').then(setStores).catch(() => {}); }, []);

  // 이미 태블릿 설정이 되어있으면 바로 메뉴로
  useEffect(() => { if (tableId) nav('/customer/menu'); }, [tableId, nav]);

  const handleCustomer = async () => {
    try {
      if (stores.length === 0) { setError('등록된 매장이 없습니다'); return; }
      const store = stores[0];
      const res = await api.post<{ storeId: number; tableId: number; sessionToken: string | null }>('/auth/table', {
        storeCode: store.storeCode, tableNumber: 1,
      });
      setTableAuth(store.storeCode, 1, res.tableId, res.storeId, res.sessionToken);
      nav('/customer/menu');
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : '고객 인증 실패');
    }
  };

  const handleStoreAdmin = () => setShowStoreSelect(true);

  const selectStore = (storeId: number) => {
    setRole('storeAdmin');
    setSelectedStore(storeId);
    nav('/admin/dashboard');
  };

  return (
    <div className="container" style={{ maxWidth: 500, marginTop: 80 }}>
      <div className="card text-center">
        <h1 className="text-xl font-bold mb-16">☕ 테이블오더</h1>
        <p className="text-gray mb-16">역할을 선택해주세요</p>
        {error && <p style={{ color: 'red', marginBottom: 8 }}>{error}</p>}
        <div className="grid" style={{ gap: 12 }}>
          <Button onClick={handleCustomer} data-testid="role-customer" style={{ padding: 16 }}>🍽️ 고객</Button>
          <Button variant="secondary" onClick={handleStoreAdmin} data-testid="role-store-admin" style={{ padding: 16 }}>🏪 매장 관리자</Button>
          <Button variant="secondary" onClick={() => { setRole('superAdmin'); nav('/super/stores'); }} data-testid="role-super-admin" style={{ padding: 16 }}>⚙️ 슈퍼 관리자</Button>
        </div>
      </div>

      {showStoreSelect && (
        <div className="card mt-16">
          <h3 className="font-bold mb-8">매장 선택</h3>
          {stores.length === 0 ? <p className="text-gray">등록된 매장이 없습니다</p> : (
            stores.map((s) => (
              <Button key={s.id} variant="secondary" onClick={() => selectStore(s.id)} style={{ width: '100%', marginBottom: 8 }} data-testid={`select-store-${s.id}`}>
                {s.name} ({s.storeCode})
              </Button>
            ))
          )}
        </div>
      )}
    </div>
  );
}
