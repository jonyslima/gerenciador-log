import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { LogService } from 'src/app/services/log.service';
@Component({
  selector: 'app-log-form',
  templateUrl: './log-form.component.html',
  styleUrls: ['./log-form.component.sass']
})
export class LogFormComponent implements OnInit {
  form: FormGroup;
  message: string;
  typeMessage: string;
  showMessage: boolean = false;

  constructor(private builder: FormBuilder, private route: ActivatedRoute, private service: LogService) { }

  ngOnInit(): void {
    this.createForm();
    if (this.idLog) {
      this.service.findById(this.idLog).subscribe(response => {
        this.form.patchValue(response);
        console.log(response);
      });
    }
  }

  createForm() {
    this.form = this.builder.group({
      ip: this.builder.control('', [Validators.required, Validators.pattern(/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/)]),
      data: this.builder.control("", [Validators.required,Validators.pattern(/^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}.\d{3}$/)]),
      userAgent: this.builder.control("", [Validators.required]),
      status: this.builder.control("", [Validators.required, Validators.min(100), Validators.max(599)])
    });
  }


  get controls() {
    return this.form.controls;
  }

  save() {
    let oservable: Observable<any> = null;
    if (this.idLog) {
      oservable = this.service.update(this.idLog, this.form.value);
    } else {
      oservable = this.service.create(this.form.value);
    }

    oservable.subscribe(response => {
      this.message = "Log salvo com sucesso";
      this.typeMessage = "success";
      this.showMessage = true;
      if (!this.idLog) {
        this.form.reset()
      }
      console.log("salvar")
    }, () => {
      this.typeMessage = "danger";
      this.message = "Erro ao salvar log";
      this.showMessage = true;
    });
  }

  showErro(erro, control) {
    return control.errors && control.errors[erro] && (control.dirty || control.touched);
  }

  get idLog() {
    return this.route.snapshot.paramMap.get('id');
  }

  close() {
    this.showMessage = false;
  }
}
