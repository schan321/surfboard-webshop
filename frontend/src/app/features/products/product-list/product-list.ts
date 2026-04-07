import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ProductService } from '../../../core/services/product.service';
import { Product } from '../../../core/models/product.model';
import { Category } from '../../../core/models/category.model';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-product-list',
  imports: [CommonModule, TranslateModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.scss',
})
export class ProductList implements OnInit {
  protected products: Product[] = [];
  protected categories: Category[] = [];
  protected selectedCategory: number | null = null;
  protected loading = true;
  protected toastMessage = '';

  constructor(
    private productService: ProductService,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  private loadProducts(): void {
    this.loading = true;
    this.productService.getAll().subscribe({
      next: (products) => {
        this.products = products;
        this.extractCategories(products);
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.loading = false;
        this.cdr.detectChanges();
      },
    });
  }

  private extractCategories(products: Product[]): void {
    const seen = new Set<number>();
    this.categories = products
      .filter((p) => {
        if (seen.has(p.category.id)) return false;
        seen.add(p.category.id);
        return true;
      })
      .map((p) => ({ id: p.category.id, name: p.category.name }));
  }

  protected selectCategory(categoryId: number | null): void {
    this.selectedCategory = categoryId;
    if (categoryId === null) {
      this.productService.getAll().subscribe({
        next: (products) => {
          this.products = products;
          this.cdr.detectChanges();
        },
        error: (err) => console.log('error:', err),
      });
    } else {
      this.productService.getByCategory(categoryId).subscribe({
        next: (products) => {
          this.products = products;
          this.cdr.detectChanges();
        },
        error: (err) => console.log('error:', err),
      });
    }
  }

  protected onSearch(event: Event): void {
    const query = (event.target as HTMLInputElement).value;
    if (query.length > 2) {
      this.productService.search(query).subscribe((products) => {
        this.products = products;
        this.cdr.detectChanges();
      });
    } else if (query.length === 0) {
      this.loadProducts();
    }
  }

  protected scrollToProducts(): void {
    const el = document.querySelector('.filters');
    if (el) {
      el.scrollIntoView({ behavior: 'smooth' });
    } else {
      window.scrollTo({ top: 600, behavior: 'smooth' });
    }
  }

  protected goToProduct(id: number): void {
    this.router.navigate(['/products', id]);
  }

  protected onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    img.onerror = null;
    img.style.display = 'none';
  }
}
