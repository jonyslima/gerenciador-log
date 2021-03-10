import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Log } from 'src/app/models/log.model';
import { Page } from 'src/app/models/page';
import { LogService } from 'src/app/services/log.service';
@Component({
  selector: 'app-list-log',
  templateUrl: './list-log.component.html',
  styleUrls: ['./list-log.component.css']
})
export class ListLogComponent implements OnInit {
  form: FormGroup;
  logs: Log[];
  totalElements: number = 0;
  pageSize: number = 10;
  page: number = 1;
  
  constructor(private service: LogService, private builder: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.builder.group({
      ip: [""],
      startDate: [""],
      endDate: [""],
      userAgent: [""],
      status: [""]
    });

    this.search(this.page);
  }

  delete(id: number) {
    this.service.delete(id).subscribe(() => {
      this.search(this.page);
    });

  }

  search(page?: number) {

    let query = this.form.value;
    query.page = page - 1;
    query.size = this.pageSize;
    query.sort = "data,desc";

    this.service.listLogs(this.form.value).subscribe(reponse => {
      let page: Page<Log> = reponse;
      this.page = page.pageable.pageNumber + 1;
      this.totalElements = page.totalElements;
      this.pageSize = page.size;
      this.logs = page.content;
    });

  }

  changePage(event) {
    this.search(event);
  }

}
