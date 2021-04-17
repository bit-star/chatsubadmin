/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import OperationLogDetailComponent from '@/entities/operation-log/operation-log-details.vue';
import OperationLogClass from '@/entities/operation-log/operation-log-details.component';
import OperationLogService from '@/entities/operation-log/operation-log.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('OperationLog Management Detail Component', () => {
    let wrapper: Wrapper<OperationLogClass>;
    let comp: OperationLogClass;
    let operationLogServiceStub: SinonStubbedInstance<OperationLogService>;

    beforeEach(() => {
      operationLogServiceStub = sinon.createStubInstance<OperationLogService>(OperationLogService);

      wrapper = shallowMount<OperationLogClass>(OperationLogDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { operationLogService: () => operationLogServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOperationLog = { id: 123 };
        operationLogServiceStub.find.resolves(foundOperationLog);

        // WHEN
        comp.retrieveOperationLog(123);
        await comp.$nextTick();

        // THEN
        expect(comp.operationLog).toBe(foundOperationLog);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOperationLog = { id: 123 };
        operationLogServiceStub.find.resolves(foundOperationLog);

        // WHEN
        comp.beforeRouteEnter({ params: { operationLogId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.operationLog).toBe(foundOperationLog);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
