import { BaseEntity } from './../../shared';

export class WeddingHostJoAngular implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public appUserId?: number,
        public foodProposalAcceptHostId?: number,
    ) {
    }
}
