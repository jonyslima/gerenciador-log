import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ChartDataSets } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import { LogService } from 'src/app/services/log.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.sass']
})
export class DashboardComponent implements OnInit {

  lineChartData: ChartDataSets[] = [{ data: [], label: 'Requisições por IP' },
  ];

  lineChartLabels: Label[] = [];

  lineChartOptions = {
    responsive: true,
  };

  lineChartColors: Color[] = [
    {
      borderColor: 'black',
      backgroundColor: 'rgba(255,255,0,0.28)',
    },
  ];

  lineChartLegend = true;
  lineChartPlugins = [];
  lineChartType = 'line';
  form: FormGroup;
  constructor(private logService: LogService, private builder: FormBuilder) { }

  ngOnInit(): void {
    this.createForm();
  }


  createForm() {
    this.form = this.builder.group({
      ip: this.builder.control('', []),
    });
  }

  search() {

    this.logService.dashboard(this.form.value).subscribe(response => {
      let data = [];
      let label = []

      response.forEach(e => {
        data.push(e.quantity);
        label.push(e.hour);

        this.lineChartData = [{ data: data, label: 'Requisições por IP' }];
        this.lineChartLabels = label;
      });

    });

  }
}
