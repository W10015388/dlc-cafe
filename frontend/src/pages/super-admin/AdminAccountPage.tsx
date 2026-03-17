import { useState, useEffect } from 'react';
import { api } from '../../services/api';
import type { StoreAdmin, Store } from '../../types';
import SuperAdminLayout from '../../components/templates/SuperAdminLayout';
import Button from '../../components/atoms/Button';

export default function AdminAccountPage() {
  const [admins, setAdmins] = useState<StoreAdmin[]>([]);
  const [stores, setStores] = useState<Store[]>([]);
  const [username, setUsername] = useState('');
  const [storeId, setStoreId] = useState('');

  const load = () => {
    api.get<StoreAdmin[]>('/store-admins').then(setAdmins);
    api.get<Store[]>('/stores').then(setStores);
  };
  useEffect(load, []);

  const handleCreate = async () => {
    await api.post('/store-admins', { username, storeId: Number(storeId) });
    setUsername(''); setStoreId(''); load();
  };

  const handleDelete = async (id: number) => {
    if (!confirm('삭제하시겠습니까?')) return;
    await api.delete(`/store-admins/${id}`); load();
  };

  return (
    <SuperAdminLayout>
      <h2 className="text-xl font-bold mb-16">관리자 계정</h2>
      <div className="card mb-16">
        <div className="flex gap-8" style={{ alignItems: 'end' }}>
          <div className="form-group" style={{ flex: 1, marginBottom: 0 }}>
            <label>사용자명</label>
            <input value={username} onChange={(e) => setUsername(e.target.value)} data-testid="admin-username" />
          </div>
          <div className="form-group" style={{ flex: 1, marginBottom: 0 }}>
            <label>매장</label>
            <select value={storeId} onChange={(e) => setStoreId(e.target.value)} data-testid="admin-store">
              <option value="">선택</option>
              {stores.map((s) => <option key={s.id} value={s.id}>{s.name}</option>)}
            </select>
          </div>
          <Button onClick={handleCreate} data-testid="admin-create">생성</Button>
        </div>
      </div>
      {admins.map((a) => (
        <div key={a.id} className="card mb-8 flex-between" data-testid={`admin-${a.id}`}>
          <div>
            <span className="font-bold">{a.username}</span>
            <span className="text-sm text-gray" style={{ marginLeft: 8 }}>매장ID: {a.storeId}</span>
          </div>
          <Button variant="danger" onClick={() => handleDelete(a.id)} data-testid={`delete-admin-${a.id}`}>삭제</Button>
        </div>
      ))}
    </SuperAdminLayout>
  );
}
