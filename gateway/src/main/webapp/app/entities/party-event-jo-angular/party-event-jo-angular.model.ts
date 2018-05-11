import { BaseEntity } from './../../shared';

export const enum Language {
    'GERMAN',
    'ENGLISH'
}

export class PartyEventJoAngular implements BaseEntity {
    constructor(
        public id?: number,
        public eventName?: string,
        public eventShortDescription?: string,
        public eventLongDescription?: any,
        public language?: Language,
        public eventInitTime?: any,
        public eventStartTime?: any,
        public eventEndTime?: any,
        public eventMaxPerson?: number,
        public weight?: number,
        public ownerId?: number,
    ) {
    }
}
