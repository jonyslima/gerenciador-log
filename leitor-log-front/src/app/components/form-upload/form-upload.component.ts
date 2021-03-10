import { Component, OnInit } from '@angular/core';
import { ChartDataSets } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import { LogService } from 'src/app/services/log.service';

@Component({
  selector: 'app-form-upload',
  templateUrl: './form-upload.component.html',
  styleUrls: ['./form-upload.component.sass']
})
export class FormUploadComponent implements OnInit {
  fileToUpload: File = null;
  sending: boolean = false;
  message: string;
  typeMessage: string;
  showMessage: boolean = false;
  constructor(private service: LogService) { }

  ngOnInit(): void {
  }


  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);

  }


  send() {
    const formData: FormData = new FormData();
    formData.append('file', this.fileToUpload, this.fileToUpload.name);
    this.sending = true;
    this.service.upload(formData).subscribe(() => {
      console.log('ok');
      this.sending = false;

      this.message = "Arquivo importado com sucesso";
      this.typeMessage = "success";
      this.showMessage = true;
    }, () => {
      this.sending = false;
      this.typeMessage = "danger";
      this.message = "Erro ao realizar ação";
      this.showMessage = true;
    });
  }

  
}
