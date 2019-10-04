import { NgModule } from '@angular/core';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { RouterModule, Routes } from '@angular/router';

const authRoutes: Routes=[
  {
    path:'',
    children:[
      {
        path:'',
        redirectTo: '/login',
        pathMatch: 'full'
      },
      {
        path:'register',
        component:RegisterComponent
      },
      {
        path:'login',
        component:LoginComponent
      }
    ]
  }
];

@NgModule({
    imports: [RouterModule.forRoot(authRoutes)],
    exports: [RouterModule]
})

export class AuthenticationRoutingModule{

}

