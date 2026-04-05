import {OrderItem} from './orderItem.model';

export interface Order {
  id: number;
  createdAt: string;
  total: number;
  status: string;
  shippingAddress: string;
  items: OrderItem[];
}
