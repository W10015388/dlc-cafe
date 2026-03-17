export interface Store {
  id: number;
  name: string;
  storeCode: string;
  createdAt: string;
}

export interface StoreAdmin {
  id: number;
  username: string;
  storeId: number;
  createdAt: string;
}

export interface Category {
  id: number;
  name: string;
  displayOrder: number;
}

export interface OptionItem {
  id: number;
  name: string;
  additionalPrice: number;
  displayOrder: number;
}

export interface OptionGroup {
  id: number;
  name: string;
  selectionType: 'SINGLE' | 'MULTIPLE';
  required: boolean;
  displayOrder: number;
  options: OptionItem[];
}

export interface Menu {
  id: number;
  categoryId: number;
  name: string;
  price: number;
  description: string;
  imageUrl: string | null;
  displayOrder: number;
  optionGroups: OptionGroup[];
}

export interface OrderItemOption {
  optionGroupName: string;
  optionName: string;
  additionalPrice: number;
}

export interface OrderItem {
  id: number;
  menuId: number;
  menuName: string;
  quantity: number;
  unitPrice: number;
  options: OrderItemOption[];
}

export interface Order {
  id: number;
  sessionId: number;
  orderNumber: string;
  status: 'PENDING' | 'PREPARING' | 'COMPLETED';
  totalAmount: number;
  createdAt: string;
  items: OrderItem[];
}

export interface OrderHistory {
  id: number;
  tableId: number;
  sessionId: number;
  orderNumber: string;
  orderData: string;
  totalAmount: number;
  orderedAt: string;
  completedAt: string;
}

export interface CartItem {
  cartItemId: string;
  menuId: number;
  menuName: string;
  price: number;
  quantity: number;
  selectedOptions: { optionId: number; groupName: string; optionName: string; additionalPrice: number }[];
  unitPrice: number;
}

export interface TableInfo {
  id: number;
  storeId: number;
  tableNumber: number;
}
