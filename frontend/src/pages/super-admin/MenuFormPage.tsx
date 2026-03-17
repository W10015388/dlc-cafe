import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { api } from '../../services/api';
import type { Menu, Category } from '../../types';
import SuperAdminLayout from '../../components/templates/SuperAdminLayout';
import Button from '../../components/atoms/Button';

export default function MenuFormPage() {
  const { id } = useParams();
  const isEdit = !!id;
  const nav = useNavigate();
  const [categories, setCategories] = useState<Category[]>([]);
  const [categoryId, setCategoryId] = useState('');
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const [description, setDescription] = useState('');
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [error, setError] = useState('');

  useEffect(() => { api.get<Category[]>('/categories').then(setCategories); }, []);

  useEffect(() => {
    if (isEdit) {
      api.get<Menu>(`/menus/${id}`).then((m) => {
        setCategoryId(String(m.categoryId)); setName(m.name);
        setPrice(String(m.price)); setDescription(m.description || '');
      });
    }
  }, [id, isEdit]);

  const handleSubmit = async () => {
    try {
      const body = { categoryId: Number(categoryId), name, price: Number(price), description, displayOrder: 0 };
      let menuId: number;
      if (isEdit) {
        const res = await api.put<Menu>(`/menus/${id}`, body);
        menuId = res.id;
      } else {
        const res = await api.post<Menu>('/menus', body);
        menuId = res.id;
      }
      if (imageFile) await api.upload(`/menus/${menuId}/image`, imageFile);
      nav('/super/menus');
    } catch (e: unknown) { setError(e instanceof Error ? e.message : '저장 실패'); }
  };

  return (
    <SuperAdminLayout>
      <h2 className="text-xl font-bold mb-16">{isEdit ? '메뉴 수정' : '메뉴 등록'}</h2>
      {error && <p style={{ color: 'red', marginBottom: 8 }}>{error}</p>}
      <div className="card">
        <div className="form-group">
          <label>카테고리</label>
          <select value={categoryId} onChange={(e) => setCategoryId(e.target.value)} data-testid="menu-category">
            <option value="">선택</option>
            {categories.map((c) => <option key={c.id} value={c.id}>{c.name}</option>)}
          </select>
        </div>
        <div className="form-group"><label>메뉴명</label><input value={name} onChange={(e) => setName(e.target.value)} data-testid="menu-name" /></div>
        <div className="form-group"><label>가격</label><input type="number" value={price} onChange={(e) => setPrice(e.target.value)} data-testid="menu-price" /></div>
        <div className="form-group"><label>설명</label><input value={description} onChange={(e) => setDescription(e.target.value)} data-testid="menu-description" /></div>
        <div className="form-group"><label>이미지</label><input type="file" accept="image/jpeg,image/png" onChange={(e) => setImageFile(e.target.files?.[0] || null)} data-testid="menu-image" /></div>
        <div className="flex gap-8">
          <Button variant="secondary" onClick={() => nav('/super/menus')} style={{ flex: 1 }}>취소</Button>
          <Button onClick={handleSubmit} style={{ flex: 2 }} data-testid="menu-submit">{isEdit ? '수정' : '등록'}</Button>
        </div>
      </div>
    </SuperAdminLayout>
  );
}
