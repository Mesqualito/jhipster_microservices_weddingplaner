import { BaseEntity } from './../../shared';

export const enum Language {
    'GERMAN',
    'ENGLISH'
}

export class PartyFoodJoAngular implements BaseEntity {
    constructor(
        public id?: number,
        public foodName?: string,
        public foodShortDescription?: string,
        public foodLongDescription?: any,
        public language?: Language,
        public foodQuantityPersons?: number,
        public foodBestServeTime?: any,
        public foodProposalAccepted?: boolean,
        public foodProposalAcceptTime?: any,
        public weight?: number,
        public ownerId?: number,
        public memberUsers?: BaseEntity[],
        public acceptedByHostId?: number,
    ) {
        this.foodProposalAccepted = false;
    }
}
