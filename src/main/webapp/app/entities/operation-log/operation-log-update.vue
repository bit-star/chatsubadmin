<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="chatsubadminApp.operationLog.home.createOrEditLabel"
          data-cy="OperationLogCreateUpdateHeading"
          v-text="$t('chatsubadminApp.operationLog.home.createOrEditLabel')"
        >
          Create or edit a OperationLog
        </h2>
        <div>
          <div class="form-group" v-if="operationLog.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="operationLog.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('chatsubadminApp.operationLog.time')" for="operation-log-time">Time</label>
            <div class="d-flex">
              <input
                id="operation-log-time"
                data-cy="time"
                type="datetime-local"
                class="form-control"
                name="time"
                :class="{ valid: !$v.operationLog.time.$invalid, invalid: $v.operationLog.time.$invalid }"
                :value="convertDateTimeFromServer($v.operationLog.time.$model)"
                @change="updateInstantField('time', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('chatsubadminApp.operationLog.chatid')" for="operation-log-chatid">Chatid</label>
            <input
              type="text"
              class="form-control"
              name="chatid"
              id="operation-log-chatid"
              data-cy="chatid"
              :class="{ valid: !$v.operationLog.chatid.$invalid, invalid: $v.operationLog.chatid.$invalid }"
              v-model="$v.operationLog.chatid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('chatsubadminApp.operationLog.setUserList')" for="operation-log-setUserList"
              >Set User List</label
            >
            <input
              type="text"
              class="form-control"
              name="setUserList"
              id="operation-log-setUserList"
              data-cy="setUserList"
              :class="{ valid: !$v.operationLog.setUserList.$invalid, invalid: $v.operationLog.setUserList.$invalid }"
              v-model="$v.operationLog.setUserList.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.operationLog.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./operation-log-update.component.ts"></script>
