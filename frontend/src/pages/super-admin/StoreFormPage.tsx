import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { api } from '../../services/api';
import type { Store } from '../../types';
import SuperAdminLayout from '../../components/templates/SuperAdminLayout';
import Button from '../../components/atoms/Button';

export default function StoreFormPage() {
  const { id } = useParams();
  const isEdit = !!id;
  const [name, setName] = useState('');
  const [storeCode, setStoreCode] = useState('');
  const [error, setError] = useState('');
  const nav = useNavigate();

  useEffect(() => {
    if (isEdit) api.get<Store>(`/stores/${id}`).then((s) => { setName(s.name); setStoreCode(s.storeCode); });
  }, [id, isEdit]);

  const handleSubmit = async () => {
    try {
      if (isEdit) await api.put(`/stores/${id}`, { name, storeCode });
      else await api.post('/stores', { name, storeCode });
      nav('/super/stores');
    } catch (e: unknown) { setError(e instanceof Error ? e.message : '저장 실패'); }
  };

  return (
    <SuperAdminLayout>
      <h2 className="text-xl font-bold mb-16">{isEdit ? '매장 수정' : '매장 등록'}</h2>
      {error && <p style={{ color: 'red', marginBottom: 8 }}>{error}</p>}
      <div className="card">
        <div className="form-group"><label>매장명</label><input value={name} onChange={(e) => setName(e.target.value)} data-testid="store-name" /></div>
        <div className="form-group"><label>매장 코드</label><input value={storeCode} onChange={(e) => setStoreCode(e.target.value)} data-testid="store-code" /></div>
        <div className="flex gap-8">
          <Button variant="secondary" onClick={() => nav('/super/stores')} style={{ flex: 1 }}>취소</Button>
          <Button onClick={handleSubmit} style={{ flex: 2 }} data-testid="store-submit">{isEdit ? '수정' : '등록'}</Button>
        </div>
      </div>
    </SuperAdminLayout>
  );
}
