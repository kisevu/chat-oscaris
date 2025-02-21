import { Component, input, InputSignal } from '@angular/core';
import { ChatResponse, UserResponse } from '../../services/models';
import { DatePipe } from '@angular/common';
import { UserService } from '../../services/services';
import { UserResponse } from '../../services/models/user-response';

@Component({
  selector: 'app-chat-list',
  imports: [DatePipe],
  templateUrl: './chat-list.component.html',
  styleUrl: './chat-list.component.scss'
})
export class ChatListComponent {

  chats: InputSignal<ChatResponse[]> = input<ChatResponse[]>([]);
  contacts: Array<UserResponse> = [];

  searchNewContact: boolean = false;

  constructor(
    private userService: UserService
  ){}

  searchContact(){
    this.userService.getUsers()
    .subscribe({
      next: (users: UserResponse []) => {
        this.contacts = users;
        this.searchNewContact = true
      }
    })
  }

  chatClicked(chat:ChatResponse){
  }

  wrapMessage(lastMessage: string | undefined ) : string {
    if(lastMessage && lastMessage.length <= 20 ){
      return lastMessage;
    }
    return lastMessage?.substring(0,17) + '...';
  }


}
