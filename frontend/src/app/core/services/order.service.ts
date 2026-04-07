import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from '../models/order.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private apiUrl = 'https://surfboard-webshop.onrender.com/api/orders';

  constructor(private http: HttpClient) {}

  placeOrder(order: any): Observable<Order> {
    return this.http.post<Order>(this.apiUrl, order);
  }

  getMyOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(this.apiUrl);
  }
}
