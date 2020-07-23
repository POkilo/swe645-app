import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';

import { Survey } from '../survey-interface.service';
import { Observable } from 'rxjs';
import { catchError, map, tap, finalize, retry } from 'rxjs/operators';
@Component({
  selector: 'app-show-survey',
  templateUrl: './show-survey.component.html',
  styleUrls: ['./show-survey.component.css']
})
export class ShowSurveyComponent implements OnInit {

  url: string = 'http://ec2-54-145-136-35.compute-1.amazonaws.com:31557/swe645-restful-api/restful/survey'
  surveys: Survey[]
  constructor(private http:HttpClient) { }

  ngOnInit(): void {
    this.getSurvey()
    .subscribe(
      res=>{
        console.log(res);
        this.surveys = res['body'];
      },
      error=>alert('not able to request the content\n')
      );
  }

  getSurvey():Observable<any>{
    return this.http.get(this.url, {observe:'response'});
  }

}