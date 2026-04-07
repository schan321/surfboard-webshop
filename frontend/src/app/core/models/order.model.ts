import {OrderItem} from './orderItem.model';

export interface Order {
  id: number;
  createdAt: string;
  total: number;
  status: string;
  street: string;
  postalCode: string;
  city: string;
  country: string;
  items: OrderItem[];
}
