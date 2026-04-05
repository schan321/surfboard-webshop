import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CartService } from '../../core/services/cart.service';
import { OrderService } from '../../core/services/order.service';
import { CartItem } from '../../core/models/cart.model';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-checkout',
  imports: [FormsModule, RouterLink, TranslateModule],
  templateUrl: './checkout.html',
  styleUrl: './checkout.scss',
})
export class Checkout implements OnInit {
  protected cartItems: CartItem[] = [];
  protected firstName = '';
  protected lastName = '';
  protected email = '';
  protected street = '';
  protected postalCode = '';
  protected city = '';
  protected country = 'Netherlands';
  protected loading = false;
  protected error = '';

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.cartService.getItems().subscribe((items) => {
      this.cartItems = items;
      if (items.length === 0) {
        this.router.navigate(['/cart']);
      }
    });
  }

  protected getTotal(): number {
    return this.cartService.getTotal();
  }

  protected placeOrder(): void {
    if (!this.street || !this.city || !this.postalCode) {
      this.error = 'Please fill in all required fields';
      return;
    }

    this.loading = true;
    this.error = '';

    const shippingAddress = `${this.firstName} ${this.lastName}, ${this.street}, ${this.postalCode} ${this.city}, ${this.country}`;

    const orderRequest = {
      shippingAddress: shippingAddress,
      items: this.cartItems.map((item) => ({
        productId: item.productId,
        quantity: item.quantity,
      })),
    };

    this.orderService.placeOrder(orderRequest).subscribe({
      next: () => {
        this.cartService.clearCart();
        this.router.navigate(['/orders']);
      },
      error: () => {
        this.loading = false;
        this.error = 'Failed to place order. Please try again.';
      },
    });
  }
}
