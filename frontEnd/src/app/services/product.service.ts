import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';

const baseUrl = 'http://localhost:8080/product/';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private http: HttpClient) {}

  create(data: any): Observable<any> {
    return this.http.post(`${baseUrl}createProduct`, data);
  }

  getAll(): Observable<Product[]> {
    return this.http.get<Product[]>(`${baseUrl}findAllProducts`);
  }

  get(id: any): Observable<Product> {
    return this.http.get<Product>(`${baseUrl}findProductById/${id}`);
  }
  
  findByTitle(title: any): Observable<Product[]> {
    return this.http.get<Product[]>(`${baseUrl}?title=${title}`);
  }

  update(id: any, data: any): Observable<any> {
    return this.http.put(`${baseUrl}updateProduct/${id}`, data);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${baseUrl}deleteProduct/${id}`);
  }

  deleteAll(): Observable<any> {
    return this.http.delete(`${baseUrl}deleteAllProducts`);
  }

}