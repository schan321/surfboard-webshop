import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../core/services/user.service';
import { User } from '../../core/models/user.model';

@Component({
  selector: 'app-profile',
  imports: [FormsModule],
  templateUrl: './profile.html',
  styleUrl: './profile.scss',
})
export class Profile implements OnInit {
  protected user: User | null = null;
  protected firstName = '';
  protected lastName = '';
  protected loading = true;
  protected saving = false;
  protected success = false;
  protected error = '';

  constructor(
    private userService: UserService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.userService.getProfile().subscribe({
      next: (user) => {
        this.user = user;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.loading = false;
      },
    });
  }

  protected save(): void {
    this.saving = true;
    this.success = false;
    this.error = '';

    this.userService
      .updateProfile({
        firstName: this.firstName,
        lastName: this.lastName,
      })
      .subscribe({
        next: (user) => {
          this.user = user;
          this.saving = false;
          this.success = true;
          setTimeout(() => (this.success = false), 3000);
        },
        error: () => {
          this.saving = false;
          this.error = 'Failed to update profile. Please try again.';
        },
      });
  }
}
