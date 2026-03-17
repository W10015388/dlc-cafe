import { useState, useEffect } from 'react';
import { api } from '../../services/api';
import type { Category } from '../../types';
import SuperAdminLayout from '../../components/templates/SuperAdminLayout';
import Button from '../../components/atoms/Button';

export default function CategoryPage() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [name, setName] = useState('');
  const [editId, setEditId] = useState<number | null>(null);
  const [editName, setEditName] = useState('');

  const load = () => api.get<Category[]>('/categories').then(setCategories);
  useEffect(load, []);

  const handleCreate = async () => {
    await api.post('/categories', { name, displayOrder: categories.length });
    setName(''); load();
  };

  const handleUpdate = async (id: number) => {
    const cat = categories.find((c) => c.id === id);
    await api.put(`/categories/${id}`, { name: editName, displayOrder: cat?.displayOrder ?? 0 });
    setEditId(null); load();
  };

  const handleDelete = async (id: number) => {
    if (!confirm('삭제하시겠습니까?')) return;
    await api.delete(`/categories/${id}`); load();
  };

  return (
    <SuperAdminLayout>
      <h2 className="text-xl font-bold mb-16">카테고리 관리</h2>
      <div className="card mb-16">
        <div className="flex gap-8">
          <input value={name} onChange={(e) => setName(e.target.value)} placeholder="카테고리명" style={{ flex: 1 }} data-testid="cat-name-input" />
          <Button onClick={handleCreate} data-testid="cat-create">추가</Button>
        </div>
      </div>
      {categories.map((c) => (
        <div key={c.id} className="card mb-8 flex-between" data-testid={`cat-${c.id}`}>
          {editId === c.id ? (
            <div className="flex gap-8" style={{ flex: 1 }}>
              <input value={editName} onChange={(e) => setEditName(e.target.value)} style={{ flex: 1 }} />
              <Button onClick={() => handleUpdate(c.id)}>저장</Button>
              <Button variant="secondary" onClick={() => setEditId(null)}>취소</Button>
            </div>
          ) : (
            <>
              <span className="font-bold">{c.name} <span className="text-sm text-gray">(순서: {c.displayOrder})</span></span>
              <div className="flex gap-8">
                <Button variant="secondary" onClick={() => { setEditId(c.id); setEditName(c.name); }} data-testid={`edit-cat-${c.id}`}>수정</Button>
                <Button variant="danger" onClick={() => handleDelete(c.id)} data-testid={`delete-cat-${c.id}`}>삭제</Button>
              </div>
            </>
          )}
        </div>
      ))}
    </SuperAdminLayout>
  );
}
