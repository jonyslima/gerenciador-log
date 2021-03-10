import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Log } from '../models/log.model';
import { Page } from '../models/page';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  constructor(private httpClient: HttpClient) { }


  listLogs(params): Observable<any> {
    return this.httpClient.get(`${environment.api}/log`,{
      params:params
    });
  }

  delete(id: Number): Observable<any> {
    return this.httpClient.delete(`${environment.api}/log/${id}`);
  }

  findById(id): Observable<any> {
    return this.httpClient.get(`${environment.api}/log/${id}`);
  }

  dashboard(params): Observable<any> {
    return this.httpClient.get(`${environment.api}/log/dashboard`, {
      params: params
    });
  }

  create(log): Observable<any> {
    return this.httpClient.post(`${environment.api}/log/`,log);
  }

  update(id,log): Observable<any> {
    return this.httpClient.put(`${environment.api}/log/${id}`,log);
  }

  upload(data:FormData): Observable<any> {
    return this.httpClient.post(`${environment.api}/log/upload`,data);
  }
}
