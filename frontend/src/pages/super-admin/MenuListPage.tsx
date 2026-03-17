import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../../services/api';
import type { Menu, Category } from '../../types';
import SuperAdminLayout from '../../components/templates/SuperAdminLayout';
import Button from '../../components/atoms/Button';

export default function MenuListPage() {
  const [menus, setMenus] = useState<Menu[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [filterCat, setFilterCat] = useState<number | null>(null);
  const nav = useNavigate();

  useEffect(() => {
    api.get<Category[]>('/categories').then(setCategories);
    api.get<Menu[]>('/menus').then(setMenus);
  }, []);

  const filtered = filterCat ? menus.filter((m) => m.categoryId === filterCat) : menus;

  const handleDelete = async (id: number) => {
    if (!confirm('메뉴를 삭제하시겠습니까?')) return;
    await api.delete(`/menus/${id}`);
    setMenus(menus.filter((m) => m.id !== id));
  };

  return (
    <SuperAdminLayout>
      <div className="flex-between mb-16">
        <h2 className="text-xl font-bold">메뉴 관리</h2>
        <Button onClick={() => nav('/super/menus/new')} data-testid="add-menu">+ 메뉴 등록</Button>
      </div>
      <div className="nav-tabs mb-16">
        <button className={`nav-tab ${!filterCat ? 'active' : ''}`} onClick={() => setFilterCat(null)}>전체</button>
        {categories.map((c) => (
          <button key={c.id} className={`nav-tab ${filterCat === c.id ? 'active' : ''}`} onClick={() => setFilterCat(c.id)}>{c.name}</button>
        ))}
      </div>
      {filtered.map((m) => (
        <div key={m.id} className="card mb-8 flex-between" data-testid={`menu-item-${m.id}`}>
          <div>
            <div className="font-bold">{m.name}</div>
            <div className="text-sm text-gray">{m.price.toLocaleString()}원 · {categories.find((c) => c.id === m.categoryId)?.name}</div>
          </div>
          <div className="flex gap-8">
            <Button variant="secondary" onClick={() => nav(`/super/menus/${m.id}/edit`)} data-testid={`edit-menu-${m.id}`}>수정</Button>
            <Button variant="danger" onClick={() => handleDelete(m.id)} data-testid={`delete-menu-${m.id}`}>삭제</Button>
          </div>
        </div>
      ))}
    </SuperAdminLayout>
  );
}
