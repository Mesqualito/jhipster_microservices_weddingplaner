import { BaseEntity } from './../../shared';

export const enum Language {
    'GERMAN',
    'ENGLISH'
}

export class AppUserJoAngular implements BaseEntity {
    constructor(
        public id?: number,
        public appUserAccount?: string,
        public appUserPassword?: string,
        public language?: Language,
        public weddingGuestId?: number,
        public weddingHostId?: number,
        public weddingServiceId?: number,
        public foodOwners?: BaseEntity[],
        public eventOwners?: BaseEntity[],
        public messageOwners?: BaseEntity[],
        public foodMemberUsers?: BaseEntity[],
        public receivedMessages?: BaseEntity[],
    ) {
    }
}
