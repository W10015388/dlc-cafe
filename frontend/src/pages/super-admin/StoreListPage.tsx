import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../../services/api';
import type { Store } from '../../types';
import SuperAdminLayout from '../../components/templates/SuperAdminLayout';
import Button from '../../components/atoms/Button';

export default function StoreListPage() {
  const [stores, setStores] = useState<Store[]>([]);
  const nav = useNavigate();

  useEffect(() => { api.get<Store[]>('/stores').then(setStores); }, []);

  const handleDelete = async (id: number) => {
    if (!confirm('매장을 삭제하시겠습니까?')) return;
    await api.delete(`/stores/${id}`);
    setStores(stores.filter((s) => s.id !== id));
  };

  return (
    <SuperAdminLayout>
      <div className="flex-between mb-16">
        <h2 className="text-xl font-bold">매장 관리</h2>
        <Button onClick={() => nav('/super/stores/new')} data-testid="add-store">+ 매장 등록</Button>
      </div>
      {stores.map((s) => (
        <div key={s.id} className="card mb-8 flex-between" data-testid={`store-${s.id}`}>
          <div>
            <div className="font-bold">{s.name}</div>
            <div className="text-sm text-gray">{s.storeCode}</div>
          </div>
          <div className="flex gap-8">
            <Button variant="secondary" onClick={() => nav(`/super/stores/${s.id}/edit`)} data-testid={`edit-store-${s.id}`}>수정</Button>
            <Button variant="danger" onClick={() => handleDelete(s.id)} data-testid={`delete-store-${s.id}`}>삭제</Button>
          </div>
        </div>
      ))}
    </SuperAdminLayout>
  );
}
