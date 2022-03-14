import { Injectable } from '@angular/core';
import { ToastController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  constructor( private toastController: ToastController ) { }

  async displayMessage( message: string ) {
    const toast = await this.toastController.create( {

        message: message,
        duration: 2000,
        position: 'top'
    } )

    toast.present()
  }
}
