import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const DdUser = () => import('@/entities/dd-user/dd-user.vue');
// prettier-ignore
const DdUserUpdate = () => import('@/entities/dd-user/dd-user-update.vue');
// prettier-ignore
const DdUserDetails = () => import('@/entities/dd-user/dd-user-details.vue');
// prettier-ignore
const OperationLog = () => import('@/entities/operation-log/operation-log.vue');
// prettier-ignore
const OperationLogUpdate = () => import('@/entities/operation-log/operation-log-update.vue');
// prettier-ignore
const OperationLogDetails = () => import('@/entities/operation-log/operation-log-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/dd-user',
    name: 'DdUser',
    component: DdUser,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user/new',
    name: 'DdUserCreate',
    component: DdUserUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user/:ddUserId/edit',
    name: 'DdUserEdit',
    component: DdUserUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user/:ddUserId/view',
    name: 'DdUserView',
    component: DdUserDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/operation-log',
    name: 'OperationLog',
    component: OperationLog,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/operation-log/new',
    name: 'OperationLogCreate',
    component: OperationLogUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/operation-log/:operationLogId/edit',
    name: 'OperationLogEdit',
    component: OperationLogUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/operation-log/:operationLogId/view',
    name: 'OperationLogView',
    component: OperationLogDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
