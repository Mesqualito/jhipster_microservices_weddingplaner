import { BaseEntity } from './../../shared';

export const enum Language {
    'GERMAN',
    'ENGLISH'
}

export class MessageJoAngular implements BaseEntity {
    constructor(
        public id?: number,
        public messageShortDescription?: string,
        public messageInitTime?: any,
        public messageText?: any,
        public language?: Language,
        public messageValidFrom?: any,
        public messageValidUntil?: any,
        public weight?: number,
        public ownerId?: number,
        public messageRecipients?: BaseEntity[],
    ) {
    }
}
