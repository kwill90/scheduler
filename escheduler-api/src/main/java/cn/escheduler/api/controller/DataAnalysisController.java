/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.escheduler.api.controller;


import cn.escheduler.api.service.DataAnalysisService;
import cn.escheduler.api.utils.Constants;
import cn.escheduler.api.utils.Result;
import cn.escheduler.dao.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

import static cn.escheduler.api.enums.Status.*;

/**
 * data analysis controller
 */
@Api(tags = "DATA_ANALYSIS_TAG", position = 1)
@RestController
@RequestMapping("projects/analysis")
public class DataAnalysisController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(DataAnalysisController.class);


    @Autowired
    DataAnalysisService dataAnalysisService;


    /**
     * statistical task instance status data
     *
     * @param loginUser
     * @param projectId
     * @return
     */
    @ApiOperation(value = "countTaskState", notes= "COUNT_TASK_STATE_NOTES")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "START_DATE", dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "END_DATE", dataType ="String"),
            @ApiImplicitParam(name = "projectId", value = "PROJECT_ID",  dataType ="Int", example = "100")
    })
    @GetMapping(value="/task-state-count")
    @ResponseStatus(HttpStatus.OK)
    public Result countTaskState(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                                         @RequestParam(value="startDate", required=false) String startDate,
                                                         @RequestParam(value="endDate", required=false) String endDate,
                                                         @RequestParam(value="projectId", required=false, defaultValue = "0") int projectId){
        try{
            logger.info("count task state, user:{}, start date: {}, end date:{}, project id {}",
                    loginUser.getUserName(), startDate, endDate, projectId);
            Map<String, Object> result = dataAnalysisService.countTaskStateByProject(loginUser,projectId, startDate, endDate);
            return returnDataList(result);
        }catch (Exception e){
            logger.error(TASK_INSTANCE_STATE_COUNT_ERROR.getMsg(),e);
            return error(TASK_INSTANCE_STATE_COUNT_ERROR.getCode(), TASK_INSTANCE_STATE_COUNT_ERROR.getMsg());
        }
    }

    /**
     * statistical process instance status data
     *
     * @param loginUser
     * @param projectId
     * @return
     */
    @ApiOperation(value = "countProcessInstanceState", notes= "COUNT_PROCESS_INSTANCE_NOTES")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "START_DATE", dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "END_DATE",  dataType ="String"),
            @ApiImplicitParam(name = "projectId", value = "PROJECT_ID",  dataType ="Int", example = "100")
    })
    @GetMapping(value="/process-state-count")
    @ResponseStatus(HttpStatus.OK)
    public Result countProcessInstanceState(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                            @RequestParam(value="startDate", required=false) String startDate,
                                            @RequestParam(value="endDate", required=false) String endDate,
                                            @RequestParam(value="projectId", required=false, defaultValue = "0") int projectId){
        try{
            logger.info("count process instance state, user:{}, start date: {}, end date:{}, project id",
                    loginUser.getUserName(), startDate, endDate, projectId);
            Map<String, Object> result = dataAnalysisService.countProcessInstanceStateByProject(loginUser, projectId, startDate, endDate);
            return returnDataList(result);
        }catch (Exception e){
            logger.error(COUNT_PROCESS_INSTANCE_STATE_ERROR.getMsg(),e);
            return error(COUNT_PROCESS_INSTANCE_STATE_ERROR.getCode(), COUNT_PROCESS_INSTANCE_STATE_ERROR.getMsg());
        }
    }

    /**
     * statistics the process definition quantities of certain person
     *
     * @param loginUser
     * @param projectId
     * @return
     */
    @ApiOperation(value = "countDefinitionByUser", notes= "COUNT_PROCESS_DEFINITION_BY_USER_NOTES")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "PROJECT_ID", dataType ="Int", example = "100")
    })
    @GetMapping(value="/define-user-count")
    @ResponseStatus(HttpStatus.OK)
    public Result countDefinitionByUser(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                        @RequestParam(value="projectId", required=false, defaultValue = "0") int projectId){
        try{
            logger.info("count process definition , user:{}, project id",
                    loginUser.getUserName(), projectId);
            Map<String, Object> result = dataAnalysisService.countDefinitionByUser(loginUser, projectId);
            return returnDataList(result);
        }catch (Exception e){
            logger.error(COUNT_PROCESS_DEFINITION_USER_ERROR.getMsg(),e);
            return error(COUNT_PROCESS_DEFINITION_USER_ERROR.getCode(), COUNT_PROCESS_DEFINITION_USER_ERROR.getMsg());
        }
    }


    /**
     * statistical command status data
     *
     * @param loginUser
     * @param projectId
     * @return
     */
    @ApiOperation(value = "countCommandState", notes= "COUNT_COMMAND_STATE_NOTES")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "START_DATE", dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "END_DATE",  dataType ="String"),
            @ApiImplicitParam(name = "projectId", value = "PROJECT_ID",  dataType ="Int", example = "100")
    })
    @GetMapping(value="/command-state-count")
    @ResponseStatus(HttpStatus.OK)
    public Result countCommandState(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                 @RequestParam(value="startDate", required=false) String startDate,
                                 @RequestParam(value="endDate", required=false) String endDate,
                                 @RequestParam(value="projectId", required=false, defaultValue = "0") int projectId){
        try{
            logger.info("count command state, user:{}, start date: {}, end date:{}, project id {}",
                    loginUser.getUserName(), startDate, endDate, projectId);
            Map<String, Object> result = dataAnalysisService.countCommandState(loginUser, projectId, startDate, endDate);
            return returnDataList(result);
        }catch (Exception e){
            logger.error(COMMAND_STATE_COUNT_ERROR.getMsg(),e);
            return error(COMMAND_STATE_COUNT_ERROR.getCode(), COMMAND_STATE_COUNT_ERROR.getMsg());
        }
    }

    /**
     * queue count
     *
     * @param loginUser
     * @param projectId
     * @return
     */
    @ApiOperation(value = "countQueueState", notes= "COUNT_QUEUE_STATE_NOTES")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "START_DATE", dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "END_DATE",  dataType ="String"),
            @ApiImplicitParam(name = "projectId", value = "PROJECT_ID",  dataType ="Int", example = "100")
    })
    @GetMapping(value="/queue-count")
    @ResponseStatus(HttpStatus.OK)
    public Result countQueueState(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                    @RequestParam(value="projectId", required=false, defaultValue = "0") int projectId){
        try{
            logger.info("count command state, user:{}, start date: {}, end date:{}, project id {}",
                    loginUser.getUserName(), projectId);
            Map<String, Object> result = dataAnalysisService.countQueueState(loginUser, projectId);
            return returnDataList(result);
        }catch (Exception e){
            logger.error(QUEUE_COUNT_ERROR.getMsg(),e);
            return error(QUEUE_COUNT_ERROR.getCode(), QUEUE_COUNT_ERROR.getMsg());
        }
    }


}
