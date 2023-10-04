import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import { AuthService } from '../../auth.service';
import { RegisterPayload } from '../../register-payload';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  registerPayload: RegisterPayload;

  // "proxyConfig": "src/proxy.conf.json"

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router:Router) {
    this.registerForm = this.formBuilder.group({
      name: '',
      username: '',
      email: '',
      password: '',
      confirmPassword: ''
    });
    this.registerPayload = {
      name: '',
      username: '',
      email: '',
      password: ''
    };
  }

  ngOnInit() {
  }

  onSubmit() {
    this.registerPayload.name = this.registerForm.get('name')!.value;
    this.registerPayload.username = this.registerForm.get('username')!.value;
    this.registerPayload.email = this.registerForm.get('email')!.value;
    this.registerPayload.password = this.registerForm.get('password')!.value;

    this.authService.register(this.registerPayload);
  }
}