import { create } from 'zustand';
import type { Order } from '../types';

interface OrderState {
  orders: Order[];
  loading: boolean;
  setOrders: (orders: Order[]) => void;
  addOrder: (order: Order) => void;
  updateOrderStatus: (orderId: number, status: Order['status']) => void;
  removeOrder: (orderId: number) => void;
  setLoading: (loading: boolean) => void;
}

export const useOrderStore = create<OrderState>((set, get) => ({
  orders: [],
  loading: false,
  setOrders: (orders) => set({ orders }),
  addOrder: (order) => set({ orders: [order, ...get().orders] }),
  updateOrderStatus: (orderId, status) =>
    set({ orders: get().orders.map((o) => (o.id === orderId ? { ...o, status } : o)) }),
  removeOrder: (orderId) => set({ orders: get().orders.filter((o) => o.id !== orderId) }),
  setLoading: (loading) => set({ loading }),
}));
