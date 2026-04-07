import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ProductService } from '../../../core/services/product.service';
import { CartService } from '../../../core/services/cart.service';
import { Product } from '../../../core/models/product.model';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-product-detail',
  imports: [RouterLink, TranslateModule],
  templateUrl: './product-detail.html',
  styleUrl: './product-detail.scss',
})
export class ProductDetail implements OnInit {
  protected product: Product | null = null;
  protected quantity = 1;
  protected loading = true;
  protected added = false;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private cartService: CartService,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.productService.getById(+id).subscribe({
        next: (product) => {
          this.product = product;
          this.loading = false;
          this.cdr.detectChanges();
        },
        error: () => (this.loading = false),
      });
    }
  }

  protected increaseQuantity(): void {
    if (this.product && this.quantity < this.product.stock) {
      this.quantity++;
    }
  }

  protected decreaseQuantity(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  protected addToCart(): void {
    if (this.product) {
      this.cartService.addItem({
        productId: this.product.id,
        productName: this.product.name,
        productPrice: this.product.price,
        imageUrl: this.product.imageUrl,
        quantity: this.quantity,
      });
      this.added = true;
      setTimeout(() => (this.added = false), 3000);
    }
  }

  protected onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    img.onerror = null;
    img.style.display = 'none';
  }
}
