import { create } from 'zustand';

interface AuthState {
  role: 'customer' | 'storeAdmin' | 'superAdmin' | null;
  storeCode: string;
  tableNumber: number | null;
  tableId: number | null;
  storeId: number | null;
  sessionToken: string | null;
  selectedStoreId: number | null;
  setTableAuth: (storeCode: string, tableNumber: number, tableId: number, storeId: number, sessionToken: string | null) => void;
  setRole: (role: AuthState['role']) => void;
  setSelectedStore: (storeId: number) => void;
  setSessionToken: (token: string) => void;
  logout: () => void;
}

export const useAuthStore = create<AuthState>()((set) => ({
  role: null,
  storeCode: '',
  tableNumber: null,
  tableId: null,
  storeId: null,
  sessionToken: null,
  selectedStoreId: null,
  setTableAuth: (storeCode, tableNumber, tableId, storeId, sessionToken) =>
    set({ storeCode, tableNumber, tableId, storeId, sessionToken, role: 'customer' }),
  setRole: (role) => set({ role }),
  setSelectedStore: (storeId) => set({ selectedStoreId: storeId }),
  setSessionToken: (token) => set({ sessionToken: token }),
  logout: () => set({ role: null, storeCode: '', tableNumber: null, tableId: null, storeId: null, sessionToken: null, selectedStoreId: null }),
}));
