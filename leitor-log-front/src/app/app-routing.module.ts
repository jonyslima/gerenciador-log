import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { FormUploadComponent } from './components/form-upload/form-upload.component';
import { ListLogComponent } from './components/list-log/list-log.component';
import { LogFormComponent } from './components/log-form/log-form.component';

const routes: Routes = [
  {
    path: "list-log", component: ListLogComponent
  },
  {
    path: "form-log", component: LogFormComponent
  },
  {
    path: "form-log/:id", component: LogFormComponent
  },
  {
    path: "form-log-upload", component: FormUploadComponent
  },
  {
    path: "dashboard", component: DashboardComponent
  },
  {
    path: "", component:DashboardComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
