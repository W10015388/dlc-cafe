import { describe, it, expect, beforeEach } from 'vitest';
import { useCartStore } from '../../stores/useCartStore';

describe('useCartStore', () => {
  beforeEach(() => {
    useCartStore.getState().clear();
  });

  it('adds item to cart', () => {
    useCartStore.getState().addItem({
      menuId: 1, menuName: '아메리카노', price: 4500, quantity: 1,
      selectedOptions: [], unitPrice: 4500,
    });
    expect(useCartStore.getState().items).toHaveLength(1);
  });

  it('merges same menu+options', () => {
    const item = { menuId: 1, menuName: '아메리카노', price: 4500, quantity: 1, selectedOptions: [], unitPrice: 4500 };
    useCartStore.getState().addItem(item);
    useCartStore.getState().addItem(item);
    expect(useCartStore.getState().items).toHaveLength(1);
    expect(useCartStore.getState().items[0].quantity).toBe(2);
  });

  it('calculates total amount', () => {
    useCartStore.getState().addItem({ menuId: 1, menuName: 'A', price: 4500, quantity: 2, selectedOptions: [], unitPrice: 4500 });
    expect(useCartStore.getState().totalAmount()).toBe(9000);
  });

  it('removes item', () => {
    useCartStore.getState().addItem({ menuId: 1, menuName: 'A', price: 4500, quantity: 1, selectedOptions: [], unitPrice: 4500 });
    const id = useCartStore.getState().items[0].cartItemId;
    useCartStore.getState().removeItem(id);
    expect(useCartStore.getState().items).toHaveLength(0);
  });

  it('clears cart', () => {
    useCartStore.getState().addItem({ menuId: 1, menuName: 'A', price: 4500, quantity: 1, selectedOptions: [], unitPrice: 4500 });
    useCartStore.getState().clear();
    expect(useCartStore.getState().items).toHaveLength(0);
  });
});
