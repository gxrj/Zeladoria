import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

import { 
    HttpEvent, 
    HttpHandler,
    HttpRequest,  
    HttpInterceptor
} from "@angular/common/http";


@Injectable( { providedIn: 'root' } )
export class CorsInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        req = req.clone( { setHeaders: { 'Access-Control-Allow-Origin': '*' } } )
        return next.handle( req )
    }
}