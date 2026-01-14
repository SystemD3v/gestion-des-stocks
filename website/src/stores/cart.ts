import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import type { CaveStock } from '@/services/api';

export interface CartItem {
  product: CaveStock;
  quantity: number;
}

export const useCartStore = defineStore('cart', () => {
  const items = ref<CartItem[]>([]);

  const totalItems = computed(() =>
    items.value.reduce((sum, item) => sum + item.quantity, 0)
  );

  const totalPrice = computed(() =>
    items.value.reduce((sum, item) => sum + item.product.price * item.quantity, 0)
  );

  function addToCart(product: CaveStock, quantity: number = 1) {
    const existingItem = items.value.find((item) => item.product.id === product.id);

    if (existingItem) {
      existingItem.quantity += quantity;
    } else {
      items.value.push({ product, quantity });
    }

    saveCart();
  }

  function removeFromCart(productId: number) {
    items.value = items.value.filter((item) => item.product.id !== productId);
    saveCart();
  }

  function updateQuantity(productId: number, quantity: number) {
    const item = items.value.find((item) => item.product.id === productId);
    if (item) {
      item.quantity = quantity;
      if (item.quantity <= 0) {
        removeFromCart(productId);
      } else {
        saveCart();
      }
    }
  }

  function clearCart() {
    items.value = [];
    saveCart();
  }

  function saveCart() {
    localStorage.setItem('cart', JSON.stringify(items.value));
  }

  function loadCart() {
    const savedCart = localStorage.getItem('cart');
    if (savedCart) {
      items.value = JSON.parse(savedCart);
    }
  }

  return {
    items,
    totalItems,
    totalPrice,
    addToCart,
    removeFromCart,
    updateQuantity,
    clearCart,
    loadCart,
  };
});
