import { useState, useEffect } from 'react';
import { api } from '../../services/api';
import { useAuthStore } from '../../stores/useAuthStore';
import { useCartStore } from '../../stores/useCartStore';
import { useNavigate } from 'react-router-dom';
import type { Category, Menu } from '../../types';
import CustomerLayout from '../../components/templates/CustomerLayout';
import Button from '../../components/atoms/Button';

const PLACEHOLDER_IMAGES = [
  '/images/menu/coffee-1.png', '/images/menu/coffee-2.png', '/images/menu/coffee-3.png',
  '/images/menu/coffee-4.png', '/images/menu/coffee-5.png', '/images/menu/coffee-6.png',
  '/images/menu/coffee-7.png', '/images/menu/coffee-8.png', '/images/menu/coffee-9.png',
];

function getMenuImage(menu: Menu, index: number) {
  return menu.imageUrl || PLACEHOLDER_IMAGES[index % PLACEHOLDER_IMAGES.length];
}

export default function MenuPage() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [selectedCat, setSelectedCat] = useState<number | null>(null);
  const [menus, setMenus] = useState<Menu[]>([]);
  const [selectedMenu, setSelectedMenu] = useState<Menu | null>(null);
  const [selectedOptions, setSelectedOptions] = useState<Record<number, number[]>>({});
  const [quantity, setQuantity] = useState(1);
  const tableId = useAuthStore((s) => s.tableId);
  const addItem = useCartStore((s) => s.addItem);
  const nav = useNavigate();

  useEffect(() => {
    if (!tableId) { nav('/'); return; }
    api.get<Category[]>('/categories').then((cats) => {
      setCategories(cats);
      if (cats.length > 0) setSelectedCat(cats[0].id);
    });
  }, [tableId, nav]);

  useEffect(() => {
    if (selectedCat) {
      api.get<Menu[]>(`/menus?categoryId=${selectedCat}`).then(setMenus);
    }
  }, [selectedCat]);

  const handleAddToCart = () => {
    if (!selectedMenu) return;
    const opts = Object.entries(selectedOptions).flatMap(([, optIds]) =>
      optIds.map((optId) => {
        for (const g of selectedMenu.optionGroups) {
          const o = g.options.find((o) => o.id === optId);
          if (o) return { optionId: o.id, groupName: g.name, optionName: o.name, additionalPrice: o.additionalPrice };
        }
        return null;
      }).filter(Boolean) as { optionId: number; groupName: string; optionName: string; additionalPrice: number }[]
    );
    const optionPrice = opts.reduce((s, o) => s + o.additionalPrice, 0);
    addItem({ menuId: selectedMenu.id, menuName: selectedMenu.name, price: selectedMenu.price, quantity, selectedOptions: opts, unitPrice: selectedMenu.price + optionPrice });
    setSelectedMenu(null);
    setSelectedOptions({});
    setQuantity(1);
  };

  const toggleOption = (groupId: number, optionId: number, selectionType: string) => {
    setSelectedOptions((prev) => {
      const current = prev[groupId] || [];
      if (selectionType === 'SINGLE') return { ...prev, [groupId]: [optionId] };
      return { ...prev, [groupId]: current.includes(optionId) ? current.filter((id) => id !== optionId) : [...current, optionId] };
    });
  };

  const calcTotal = () => {
    if (!selectedMenu) return 0;
    const optPrice = Object.values(selectedOptions).flat().reduce((sum, optId) => {
      for (const g of selectedMenu.optionGroups) {
        const o = g.options.find((o) => o.id === optId);
        if (o) return sum + o.additionalPrice;
      }
      return sum;
    }, 0);
    return (selectedMenu.price + optPrice) * quantity;
  };

  return (
    <CustomerLayout>
      <div className="menu-layout">
        {/* 좌측 카테고리 사이드바 */}
        <div className="menu-sidebar">
          {categories.map((c) => (
            <button
              key={c.id}
              className={`menu-sidebar-item ${selectedCat === c.id ? 'active' : ''}`}
              onClick={() => setSelectedCat(c.id)}
              data-testid={`cat-${c.id}`}
            >
              {c.name}
            </button>
          ))}
        </div>

        {/* 우측 메뉴 그리드 */}
        <div className="menu-grid">
          {menus.map((m, i) => (
            <div
              key={m.id}
              className="menu-card"
              onClick={() => { setSelectedMenu(m); setSelectedOptions({}); setQuantity(1); }}
              data-testid={`menu-${m.id}`}
            >
              <img src={getMenuImage(m, i)} alt={m.name} className="menu-card-img" />
              <div className="menu-card-body">
                <div className="menu-card-name">{m.name}</div>
                <div className="menu-card-desc">{m.description}</div>
                <div className="menu-card-footer">
                  <span className="menu-card-price">{m.price.toLocaleString()}원</span>
                  <button className="menu-card-add" onClick={(e) => { e.stopPropagation(); setSelectedMenu(m); setSelectedOptions({}); setQuantity(1); }}>+</button>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* 메뉴 상세 모달 */}
      {selectedMenu && (
        <div className="modal-overlay" onClick={() => setSelectedMenu(null)}>
          <div className="menu-detail-modal modal-content" onClick={(e) => e.stopPropagation()}>
            <img
              src={getMenuImage(selectedMenu, menus.indexOf(selectedMenu))}
              alt={selectedMenu.name}
              className="menu-detail-img"
            />
            <div className="menu-detail-body">
              <div className="menu-detail-name">{selectedMenu.name}</div>
              <div className="menu-detail-desc">{selectedMenu.description}</div>

              {selectedMenu.optionGroups.map((g) => (
                <div key={g.id} className="mb-16">
                  <div className="option-group-label">
                    {g.name} {g.required && <span style={{ color: '#c0785c' }}>*</span>}
                  </div>
                  <div className="flex gap-8" style={{ flexWrap: 'wrap' }}>
                    {g.options.map((o) => (
                      <button
                        key={o.id}
                        className={`option-btn ${(selectedOptions[g.id] || []).includes(o.id) ? 'selected' : ''}`}
                        onClick={() => toggleOption(g.id, o.id, g.selectionType)}
                        data-testid={`opt-${o.id}`}
                      >
                        {o.name} {o.additionalPrice > 0 && `+${o.additionalPrice.toLocaleString()}원`}
                      </button>
                    ))}
                  </div>
                </div>
              ))}

              <div className="flex-between mb-16">
                <span style={{ fontSize: 24, fontWeight: 700, color: '#3a2e22' }}>
                  {calcTotal().toLocaleString()}원
                </span>
                <div className="qty-control">
                  <button className="qty-btn" onClick={() => setQuantity(Math.max(1, quantity - 1))} data-testid="qty-minus">−</button>
                  <span style={{ fontSize: 16, fontWeight: 700, minWidth: 20, textAlign: 'center' }}>{quantity}</span>
                  <button className="qty-btn" onClick={() => setQuantity(quantity + 1)} data-testid="qty-plus">+</button>
                </div>
              </div>

              <button className="add-cart-btn" onClick={handleAddToCart} data-testid="add-to-cart">
                장바구니 담기
              </button>
            </div>
          </div>
        </div>
      )}
    </CustomerLayout>
  );
}
