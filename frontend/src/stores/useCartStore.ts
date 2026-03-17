import { create } from 'zustand';
import type { CartItem } from '../types';

interface CartState {
  items: CartItem[];
  addItem: (item: Omit<CartItem, 'cartItemId'>) => void;
  removeItem: (cartItemId: string) => void;
  updateQuantity: (cartItemId: string, quantity: number) => void;
  clear: () => void;
  totalAmount: () => number;
  totalQuantity: () => number;
}

function generateCartItemId(item: Omit<CartItem, 'cartItemId'>): string {
  const optionKey = item.selectedOptions.map((o) => o.optionId).sort().join('-');
  return `${item.menuId}_${optionKey}`;
}

export const useCartStore = create<CartState>()((set, get) => ({
  items: [],
  addItem: (item) => {
    const cartItemId = generateCartItemId(item);
    const existing = get().items.find((i) => i.cartItemId === cartItemId);
    if (existing) {
      set({ items: get().items.map((i) => i.cartItemId === cartItemId ? { ...i, quantity: i.quantity + item.quantity } : i) });
    } else {
      set({ items: [...get().items, { ...item, cartItemId }] });
    }
  },
  removeItem: (cartItemId) => set({ items: get().items.filter((i) => i.cartItemId !== cartItemId) }),
  updateQuantity: (cartItemId, quantity) => {
    if (quantity <= 0) {
      set({ items: get().items.filter((i) => i.cartItemId !== cartItemId) });
    } else {
      set({ items: get().items.map((i) => i.cartItemId === cartItemId ? { ...i, quantity } : i) });
    }
  },
  clear: () => set({ items: [] }),
  totalAmount: () => get().items.reduce((sum, i) => sum + i.unitPrice * i.quantity, 0),
  totalQuantity: () => get().items.reduce((sum, i) => sum + i.quantity, 0),
}));
