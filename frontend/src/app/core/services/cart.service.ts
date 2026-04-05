import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { CartItem } from '../models/cart.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartKey = 'cart';
  private cartItems$ = new BehaviorSubject<CartItem[]>(this.loadCart());

  getItems(): Observable<CartItem[]> {
    return this.cartItems$.asObservable();
  }

  addItem(item: CartItem): void {
    const items = this.cartItems$.getValue();
    const existing = items.find(i => i.productId === item.productId);

    if (existing) {
      existing.quantity += item.quantity;
    } else {
      items.push(item);
    }

    this.saveCart(items);
    this.cartItems$.next([...items]);
  }

  removeItem(productId: number): void {
    const items = this.cartItems$.getValue().filter(i => i.productId !== productId);
    this.saveCart(items);
    this.cartItems$.next(items);
  }

  updateQuantity(productId: number, quantity: number): void {
    const items = this.cartItems$.getValue();
    const item = items.find(i => i.productId === productId);
    if (item) {
      item.quantity = quantity;
      this.saveCart(items);
      this.cartItems$.next([...items]);
    }
  }

  clearCart(): void {
    localStorage.removeItem(this.cartKey);
    this.cartItems$.next([]);
  }

  getTotal(): number {
    return this.cartItems$.getValue()
      .reduce((total, item) => total + item.productPrice * item.quantity, 0);
  }

  private loadCart(): CartItem[] {
    const cart = localStorage.getItem(this.cartKey);
    return cart ? JSON.parse(cart) : [];
  }

  private saveCart(items: CartItem[]): void {
    localStorage.setItem(this.cartKey, JSON.stringify(items));
  }
}
