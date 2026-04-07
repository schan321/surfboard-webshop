import { Category } from './category.model';

export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  stock: number;
  volume_liter: number | null;
  length_cm: number | null;
  width_cm: number | null;
  fin_system: string | null;
  category: Category;
  imageUrl: string;
}
