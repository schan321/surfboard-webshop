import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../core/services/product.service';
import { Product } from '../../core/models/product.model';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-admin',
  imports: [FormsModule, TranslateModule],
  templateUrl: './admin.html',
  styleUrl: './admin.scss',
})
export class Admin implements OnInit {
  protected products: Product[] = [];
  protected loading = true;
  protected showForm = false;
  protected editingProduct: Product | null = null;
  protected error = '';
  protected success = '';

  protected form = {
    name: '',
    description: '',
    price: 0,
    stock: 0,
    categoryId: 1,
    imageUrl: '',
  };

  protected categories = [
    { id: 1, name: 'Shortboard' },
    { id: 2, name: 'Longboard' },
    { id: 3, name: 'Fish' },
    { id: 4, name: 'Funboard' },
  ];

  constructor(
    private productService: ProductService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  private loadProducts(): void {
    this.productService.getAll().subscribe({
      next: (products) => {
        this.products = products;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => (this.loading = false),
    });
  }

  protected openCreateForm(): void {
    this.editingProduct = null;
    this.form = { name: '', description: '', price: 0, stock: 0, categoryId: 1, imageUrl: '' };
    this.showForm = true;
  }

  protected openEditForm(product: Product): void {
    this.editingProduct = product;
    this.form = {
      name: product.name,
      description: product.description,
      price: product.price,
      stock: product.stock,
      categoryId: product.category.id,
      imageUrl: product.imageUrl,
    };
    this.showForm = true;
  }

  protected closeForm(): void {
    this.showForm = false;
    this.editingProduct = null;
    this.error = '';
  }

  protected save(): void {
    this.error = '';
    if (this.editingProduct) {
      this.productService.update(this.editingProduct.id, this.form).subscribe({
        next: () => {
          this.success = 'Product updated successfully!';
          this.closeForm();
          this.loadProducts();
          setTimeout(() => (this.success = ''), 3000);
        },
        error: () => (this.error = 'Failed to update product.'),
      });
    } else {
      this.productService.create(this.form).subscribe({
        next: () => {
          this.success = 'Product created successfully!';
          this.closeForm();
          this.loadProducts();
          setTimeout(() => (this.success = ''), 3000);
        },
        error: () => (this.error = 'Failed to create product.'),
      });
    }
  }

  protected delete(product: Product): void {
    if (confirm(`Are you sure you want to delete ${product.name}?`)) {
      this.productService.delete(product.id).subscribe({
        next: () => {
          this.success = 'Product deleted successfully!';
          this.loadProducts();
          setTimeout(() => (this.success = ''), 3000);
        },
        error: () => (this.error = 'Failed to delete product.'),
      });
    }
  }
}
